package net.luculent.automatioin.laks.platform.file;

public interface FileTransfer {
	
	public boolean upload(byte[] bt, String targetFileName);
	
	public boolean upload(byte[] bt, String targetFileName, String host, int port, String userName, String pw, String serverPath);
	
	public byte [] download(String sourceFileName);
	
}
