package com.jin.commons.email;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.inject.Named;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import com.jin.commons.properties.ConfigProperties;

/**
 * 邮件发送接口
 */
@Named("emailService")
public class EmailService {
	
	public static boolean isServer = false;
	
	private static Logger logger = Logger.getLogger(EmailService.class);

	
	private final String DEFAULT_ENCODING = "UTF-8";
	
	private String mailEncoding = DEFAULT_ENCODING;
	
	static String receiver;

	
	private static String host;
	private static String username;
	private static String password;
	private static String nick;
	
	/** sender */
	@Autowired
	private JavaMailSender javaMailSender;


	static{
	    try {
			Properties properties = new Properties();
			InputStream is = ConfigProperties.class.getResourceAsStream("/email.properties");
			properties.load(is);
			nick=properties.get("com.health.sys.email.nick").toString();
			host=properties.get("com.health.sys.email.host").toString();
			username=properties.get("com.health.sys.email.userName").toString();
			password=properties.get("com.health.sys.email.pwd").toString();
			receiver=properties.get("com.commons.receiver.email").toString();
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
	public void sendSimpleMail(final String subject, final String content) {
		sendSimpleMail(receiver, subject, content);
	}
	
	/**
	 * 发送邮件
	 * 调用方式 mailService.sendSimpleMail("XXX@gmail.com", "找回密码", "尊敬的用户您的密码是:" + user.getOriPassword());
	 * @param receiver 接收者邮箱
	 * @param subject 主题
	 * @param content 内容
	 */
	public void sendSimpleMail(final String receiver, final String subject, final String content) {
		if(!isServer) {
			logger.info("开发电脑不发邮件: "+subject);
			logger.info(content);
			return;
		}
		if(StringUtils.isBlank(subject)) {
			logger.error("邮件标题不能为空");
			return;
		}
		if(StringUtils.isBlank(content)) {
			logger.error("邮件内容不能为空");
			return;
		}
		try {
			javaMailSender.send(new MimeMessagePreparator() {

				public void prepare(MimeMessage message) throws Exception {
					// TODO Auto-generated method stub
					JavaMailSenderImpl javaMailSenderImpl = (JavaMailSenderImpl)javaMailSender;
					javaMailSenderImpl.setHost(host);
					javaMailSenderImpl.setUsername(username);
					javaMailSenderImpl.setPassword(password);
					
					MimeMessageHelper helper = new MimeMessageHelper(message, getMailEncoding());

					//设置自定义发件人昵称
					try {
						String nickEncode=javax.mail.internet.MimeUtility.encodeText(nick);
						helper.setFrom(new InternetAddress(nickEncode+" <"+javaMailSenderImpl.getUsername()+">"));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					} 
					
					helper.setTo(receiver);
					helper.setSubject(subject);
					helper.setText(content, true);
					helper.setSentDate(new Date());
					/*//发送图片 
					FileSystemResource res = new FileSystemResource(new File("D://1.jpg"));
					//Spring会自动查找cid:匹配后面的内容,<img src='cid:ident' />
					helper.addInline("ident", res);
					//发送附件
					helper.addAttachment(MimeUtility.encodeWord("d://1.jpg"), new File("D://1.jpg"));*/

				}
			});
		} catch (Exception e) {
			logger.error("发送邮件失败");
			logger.error("邮件标题:"+subject);
			logger.error("邮件内容:"+content);
			e.printStackTrace();
		}
	}

	public JavaMailSender getJavaMailSender() {
		return javaMailSender;
	}

	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}


	public String getMailEncoding() {
		return mailEncoding;
	}

	public void setMailEncoding(String mailEncoding) {
		this.mailEncoding = mailEncoding;
	}


}
