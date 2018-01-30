package net.luculent.automatioin.laks.platform.file;


import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.SocketException;

/**
 * @Author: zhuzb
 * @Description: 文件上传处理
 * @Date Create In 18:53 2018/1/2
 * @Modified By:
 */
@Service(value = "ftpFileTransfer")
public class FtpFileTransfer implements FileTransfer {

    public static final String PASSIVE_DATA_CONNECTION_MODE = "PASV";

    private static final Logger logger = LoggerFactory.getLogger(FtpFileTransfer.class);

    @Value("${laks.ftp.ip}")
    private String serverIp;

    @Value("${laks.ftp.port}")
    private int serverPort;

    @Value("${laks.ftp.username}")
    private String password;

    @Value("${laks.ftp.password}")
    private String userName;

    @Value("${laks.ftp.path}")
    private String serverPath;

    boolean binaryMode = true;
    boolean passiveMode = true;
    boolean logoutMode = true;


    public FtpFileTransfer() {

    }


    public byte[] download(String sourceFileName) {
        String s;
        if (serverPath != null) {
            s = serverPath + sourceFileName;
        } else {
            s = sourceFileName;
        }

        FTPClient ftpclient;

        boolean flag = false;

        ftpclient = new FTPClient();

        byte abyte0[];
        try {
            ftpclient.connect(serverIp, serverPort);
            int i = ftpclient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(i)) {
                ftpclient.disconnect();
                logger.error("ftpserver_refuse_connection");
                return null;
            }
            if (!ftpclient.login(userName, password)) {
                logger.error("ftpserver_refuse_login");
                return null;
            }
            flag = true;
            if (logger.isDebugEnabled())
                logger.debug("Remote system is " + ftpclient.getSystemName());
            if (binaryMode)
                ftpclient.setFileType(2);
            if (passiveMode)
                ftpclient.enterLocalPassiveMode();
            ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
            ftpclient.retrieveFile(s, bytearrayoutputstream);
            abyte0 = bytearrayoutputstream.toByteArray();
        } catch (IOException ioexception) {
            logger.error("fail_to_connect_ftpserver:", ioexception);
            return null;
        }
        try {
            if (logoutMode && flag)
                try {
                    ftpclient.logout();
                } catch (Exception exception1) {
                }
            if (ftpclient.isConnected())
                ftpclient.disconnect();
        } catch (IOException ioexception1) {
        }
        return abyte0;
    }


    /**
     * 直接将byte写到服务器指定目录下文件中
     */
//	@Bean
    public boolean upload(byte[] bt, String targetFileName) {
        String s;
        boolean flag = false;
        FTPClient ftpclient = new FTPClient();
        try {
            ftpclient.connect(serverIp, serverPort);
            int i = ftpclient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(i)) {
                ftpclient.disconnect();
                logger.error("ftpserver_refuse_connection");
                return false;
            }
            if (!ftpclient.login(userName, password)) {
                logger.error("ftpserver_refuse_login");
                return false;
            }
            flag = true;

            if (logger.isDebugEnabled())
                logger.debug("Remote system is " + ftpclient.getSystemName());
            if (binaryMode)
                ftpclient.setFileType(2);
            if (passiveMode)
                ftpclient.enterLocalPassiveMode();

            s = serverPath + targetFileName;
            boolean success = ftpclient.storeFile(s, new ByteArrayInputStream(bt));
            return success;
        } catch (SocketException e) {
            logger.error("FtpTransferUploadErr", e);
        } catch (IOException e) {
            logger.error("FtpTransferUploadIOErr", e);
        } finally {
            try {
                if (logoutMode && flag)
                    try {
                        ftpclient.logout();
                    } catch (Exception exception1) {
                    }
                if (ftpclient.isConnected())
                    ftpclient.disconnect();
            } catch (IOException ioexception1) {
                logger.error("FtpLogoutErr", ioexception1);
            }
        }
        return flag;
    }


    /* 将byte写到服务器指定目录下
     *
     */
    public boolean upload(byte[] bt, String targetFileName, String host, int port, String userName, String pw, String serverPath) {
        String s;
        boolean flag = false;
        FTPClient ftpclient = new FTPClient();
        try {
            ftpclient.connect(host, port);
            int i = ftpclient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(i)) {
                ftpclient.disconnect();
                logger.error("ftpserver_refuse_connection");
                return false;
            }
            if (!ftpclient.login(userName, pw)) {
                logger.error("ftpserver_refuse_login");
                return false;
            }
            flag = true;
            if (logger.isDebugEnabled())
                logger.debug("Remote system is " + ftpclient.getSystemName());
            if (binaryMode)
                ftpclient.setFileType(2);
            if (passiveMode)
                ftpclient.enterLocalPassiveMode();

            s = serverPath + targetFileName;
            boolean success = ftpclient.storeFile(s, new ByteArrayInputStream(bt));
            return success;
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (logoutMode && flag)
                    try {
                        ftpclient.logout();
                    } catch (Exception exception1) {
                    }
                if (ftpclient.isConnected())
                    ftpclient.disconnect();
            } catch (IOException ioexception1) {
            }
        }
        return true;
    }

    /**
     * 判断文件是否存在
     *
     * @param sourceFileName
     * @return
     */
    public boolean checkFileIsExist(String sourceFileName) {
        FTPClient ftpclient = new FTPClient();
        try {
            // 替换登陆
            ftpclient.connect(serverIp, serverPort);
            int i = ftpclient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(i)) {
                ftpclient.disconnect();
                logger.error("ftpserver_refuse_connection");
                return false;
            }
            if (!ftpclient.login(userName, password)) {
                logger.error("ftpserver_refuse_login");
                return false;
            }

//				TelnetInputStream fget = null;

//				log.info("ftp appoint file is "+this.serverPath+sourceFileName);

//				fc.sendServer(FtpFileTransfer.PASSIVE_DATA_CONNECTION_MODE);
            // 替换被动模式设置
            ftpclient.enterLocalPassiveMode();
//				fget = fc.get(this.serverPath + sourceFileName);
            // 替换文件检查逻辑
            FTPFile[] result = ftpclient.listFiles(this.serverPath + sourceFileName);

//				fget.close();
            return result != null && result.length > 0;
        } catch (Exception e) {
            logger.error("the appoint file is not exist", e);
            return false;
        } finally {
//				try {
//					fc.closeServer();
//				} catch (IOException e1) {
//					e1.printStackTrace();
//				}
            // 替换关闭
            disconnect(ftpclient);
        }

//			return true;
    }


    //判断文件夹是否存在，若不存创建一个
    public void checkIsExistAndBuild(String dir) {
        FTPClient ftpclient = new FTPClient();
        try {
//			fc = new FtpClient(getServerIp());
//		    fc.login(getUserName(), getPassword());
            // 替换登陆
            ftpclient.connect(serverIp, serverPort);
            int i = ftpclient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(i)) {
                ftpclient.disconnect();
                logger.error("ftpserver_refuse_connection");
            }
            if (!ftpclient.login(userName, password)) {
                logger.error("ftpserver_refuse_login");
            }
//		    if (isDirExist(ftpclient,dir)){
//		    	
//		    }
//		    else{
//		    	createDir(ftpclient,dir);
//		    }
            if (!ftpclient.changeWorkingDirectory(dir)) {
                ftpclient.makeDirectory(dir);
            }
        } catch (Exception e) {
            logger.error("FtpFileTransferCheckAndBuildDirErr", e);
        }
    }


    /**
     * 检查文件夹在当前目录下是否存在
     *
     * @param dir
     * @return
     * @throws IOException
     */
    public boolean isDirExist(FTPClient fc, String dir) throws IOException {
        try {
            return fc.changeWorkingDirectory(dir);
        } catch (IOException e) {
            logger.error("Check Dir Err", e);
            throw e;
        }
    }

    /**
     * 在当前目录下创建文件夹
     *
     * @param dir
     * @return
     * @throws Exception
     */
    public boolean createDir(FTPClient fc, String dir) throws IOException {
        try {
            return fc.makeDirectory(dir);
        } catch (IOException e) {
            logger.error("CreateDir Err", e);
            throw e;
        }
    }

    /**
     * 关闭连接
     *
     * @param ftp
     */
    private void disconnect(FTPClient ftp) {
        try {
            ftp.logout();
        } catch (IOException e) {
            logger.error("", e);
        } finally {
            try {
                ftp.disconnect();
            } catch (IOException e) {
                logger.error("", e);
            }
        }
    }


}
