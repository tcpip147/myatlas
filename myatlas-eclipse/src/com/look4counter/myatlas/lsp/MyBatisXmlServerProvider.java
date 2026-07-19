package com.look4counter.myatlas.lsp;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.lsp4e.server.ProcessStreamConnectionProvider;
import org.osgi.framework.Bundle;

public class MyBatisXmlServerProvider extends ProcessStreamConnectionProvider {

	public MyBatisXmlServerProvider() {
		List<String> command = new ArrayList<>();
		command.add("java");
		command.add("-jar");
		command.add(getBundleFilePath("lib/org.eclipse.lemminx.uber-jar_0.31.0.jar"));
		setCommands(command);
	}

	private String getBundleFilePath(String relativePath) {
		Bundle bundle = Platform.getBundle("myatlas-eclipse");
		URL fileURL = bundle.getEntry(relativePath);
		try {
			return new File(FileLocator.resolve(fileURL).toURI()).getAbsolutePath();
		} catch (Exception e) {
			throw new RuntimeException("File not found" + relativePath, e);
		}
	}
}
