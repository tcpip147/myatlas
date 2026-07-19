package com.look4counter.myatlas.lsp;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
	
	

	@Override
	public InputStream getInputStream() {
		InputStream originalStream = super.getInputStream(); // 혹은 원래 사용하던 스트림

		// 스트림을 읽을 때마다 콘솔에 출력하는 커스텀 스트림으로 감싸서 리턴
		return new java.io.FilterInputStream(originalStream) {
			@Override
			public int read(byte[] b, int off, int len) throws IOException {
				int bytesRead = super.read(b, off, len);
				if (bytesRead > 0) {
					// System.out.print("[LSP ➡️ Eclipse] " + new String(b, off, bytesRead,
					// "UTF-8"));
				}
				return bytesRead;
			}
		};
	}

	@Override
	public OutputStream getOutputStream() {
		OutputStream originalOut = super.getOutputStream(); // 원래 이클립스가 서버로 보내는 파이프

		// 이클립스가 서버로 데이터를 보낼 때(write), 중간에 가로채서 이클립스 시스템 콘솔(Sysout)에도 같이 쏴줍니다.
		return new java.io.FilterOutputStream(originalOut) {
			@Override
			public void write(byte[] b, int off, int len) throws IOException {
				super.write(b, off, len); // 원래 서버로 보냄

				// 💡 드디어 개발자 콘솔 뷰(System.out)에 강제로 출력!
				String jsonMessage = new String(b, off, len, "UTF-8");
				// System.out.println("[이클립스 ➡️ 서버 전송 데이터]: " + jsonMessage);
			}

			@Override
			public void write(int b) throws IOException {
				super.write(b);
			}
		};
	}

}
