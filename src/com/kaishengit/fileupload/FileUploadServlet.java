package com.kaishengit.fileupload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.IOUtils;

@WebServlet("/fileupload")
public class FileUploadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/views/upload.jsp").forward(req, resp);
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		
		//�����ļ��ϴ�λ��
		File savePath = new File("D:/ANewFile");
		if(!savePath.exists()){
			savePath.mkdirs();
		}
		
		//���û����ļ���
		File tempPath = new File("D:/ANewFileTemp");
		if(!tempPath.exists()){
			tempPath.mkdirs();
		}
		
		//�ж�form�����Ƿ���enctype����
		if(ServletFileUpload.isMultipartContent(req)){
			
			//���û����ļ�����
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(1024);
			factory.setRepository(tempPath);
			
			//�����ϴ��ļ��Ĵ�С
			ServletFileUpload servletFileUpload = new ServletFileUpload(factory);
			servletFileUpload.setFileSizeMax(1024*1024*200);
			
			try {
				List<FileItem> itemList = servletFileUpload.parseRequest(req);
				
				for(FileItem item : itemList){
					
					if(item.isFormField()){
						System.out.println("commons fileName: "+item.getFieldName());
						System.out.println("commons name: "+item.getString());
					}else{
						System.out.println("fieldname: "+item.getFieldName());
						System.out.println("name: "+item.getName());
						
						InputStream inputStream = item.getInputStream();
						
						String filename1 = item.getName();
						String newFileName = filename1.substring(0, filename1.indexOf(".")) + UUID.randomUUID() + filename1.substring(filename1.lastIndexOf("."));
						
						FileOutputStream outputStream = new FileOutputStream(new File(savePath,newFileName));
						IOUtils.copy(inputStream, outputStream);
						
						outputStream.flush();
						outputStream.close();
						inputStream.close();
					}
					
				}
				
			} catch (FileUploadException e) {
				e.printStackTrace();
			}
			
		}else{
			throw new RuntimeException();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		
		//���������ļ���
		File savePath = new File("D:/ANewFile");
		if(!savePath.exists()){
			savePath.mkdirs();
		}
		
		//���������ļ���
		File tempPath = new File("D:/ANewFileTemp");
		if(!tempPath.exists()){
			tempPath.mkdirs();
		}
		
		//�ж�form���Ƿ���enctype����
		if(ServletFileUpload.isMultipartContent(req)){
			
			//���û�����
			DiskFileItemFactory itemFactory = new DiskFileItemFactory();
			itemFactory.setSizeThreshold(1024);  //���û�������С
			itemFactory.setRepository(tempPath);  //���û����ļ���
			
			//�����ļ��ϴ�
			ServletFileUpload servletFileUpload = new ServletFileUpload(itemFactory);
			servletFileUpload.setFileSizeMax(1024*1024*100);  //�趨�ϴ��ļ��Ĵ�С
			
			try{
				List<FileItem> itemList = servletFileUpload.parseRequest(req);
				for(FileItem item : itemList){
					if(item.isFormField()){
						//TODO Ϊʲô�ⲿ��Ҳ�����
						System.out.println("what's this: "+item.getFieldName());
						System.out.println("FFName: "+item.getName());
					}else{
						System.out.println("FileName: "+item.getFieldName());
						System.out.println("Name: "+item.getName());
						
						InputStream inputStream = item.getInputStream();
						
						String newFileName = item.getName().substring(0, item.getName().indexOf(".")+1)+UUID.randomUUID()+item.getName().substring(item.getName().lastIndexOf("."));					
						FileOutputStream outputStream = new FileOutputStream(new File(savePath,newFileName)); 
						
						IOUtils.copy(inputStream, outputStream);
						
						outputStream.flush();
						outputStream.close();
						inputStream.close();
					}
			} 
			}catch(FileUploadException e){
				e.printStackTrace();
			}
			
		}else{
			throw new RuntimeException();
		}
	}*/
	
	
	
	
	
	
	
	
	
	/*@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		//1.�ļ��ϴ���Ĵ��·��
		File savePath = new File("D:/ANewFile");
		if(!savePath.exists()){
			savePath.mkdirs();
		}
		
		//2.�����ļ��ϴ�����ʱ·��
		File tempPath = new File("D:/ANewFileTemp");
		if(!tempPath.exists()){
			tempPath.mkdirs();
		}
		
		//3.�жϱ��Ƿ���enctype����
		if(ServletFileUpload.isMultipartContent(req)){
			
			//Ϊ�����ļ�����һ������
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(1024);  //��������С
			factory.setRepository(tempPath);  //��ʱ�ļ�·��
			
			//�����ϴ��ļ��Ĵ�����
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setSizeMax(1024*1024*20);  //�ϴ��ļ���������
			
			try{
				List<FileItem> itemList = upload.parseRequest(req);
				for(FileItem item : itemList){
					
					if(item.isFormField()){
						//��ͨԪ��
						System.out.println("FieldName"+item.getFieldName());
						System.out.println("getString"+item.getString());
					}else{
						//�ļ�Ԫ��
						System.out.println("FieldName"+item.getFieldName());
						System.out.println("getName"+item.getName());
						
						InputStream inputStream = item.getInputStream();
						
						String fileName = item.getName();
						String newFileName = UUID.randomUUID().toString()+fileName.substring(fileName.lastIndexOf("."));
						
						FileOutputStream outputStream = new FileOutputStream(new File(savePath,newFileName));
						
						IOUtils.copy(inputStream, outputStream);
						outputStream.flush();
						outputStream.close();
						inputStream.close();
						
						System.out.println(item.getName() + "  -> �ļ��ϴ��ɹ���");
						req.getRequestDispatcher("/WEB-INF/views/upload.jsp").forward(req,resp);
					}
				}
			}catch(FileUploadException e) {
                e.printStackTrace();
			}
				
			
		}else{
			throw new RuntimeException("form����enctype���Դ���");
		}*/
		
		
	}


