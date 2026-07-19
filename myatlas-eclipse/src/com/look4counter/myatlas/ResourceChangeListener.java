package com.look4counter.myatlas;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IReusableEditor;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.part.FileEditorInput;

public class ResourceChangeListener implements IResourceChangeListener, IResourceDeltaVisitor {

	private EditorPart editorPart;

	public ResourceChangeListener(EditorPart editorPart) {
		this.editorPart = editorPart;
	}

	@Override
	public boolean visit(IResourceDelta delta) throws CoreException {
		IEditorInput editorInput = editorPart.getEditorInput();
		IResource resource = null;
		if (editorInput instanceof IFileEditorInput fileEditorInput) {
			resource = fileEditorInput.getFile();
		} else {
			resource = editorInput.getAdapter(IResource.class);
		}
		if (delta == null || !delta.getResource().equals(resource)) {
			return true;
		}
		if (delta.getKind() == IResourceDelta.REMOVED) {
			if ((delta.getFlags() & IResourceDelta.MOVED_TO) != 0) {
				IFile newFile = ResourcesPlugin.getWorkspace().getRoot().getFile(delta.getMovedToPath());
				if (editorPart instanceof IReusableEditor reusable) {
					
					reusable.setInput(new FileEditorInput(newFile));
				}
			}
		}
		return false;
	}

	@Override
	public void resourceChanged(IResourceChangeEvent e) {
		IResourceDelta delta = e.getDelta();
		if (delta != null) {
			try {
				delta.accept(this);
			} catch (CoreException e1) {
			}
		}
	}
}