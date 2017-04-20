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
		
		//设置文件上传位置
		File savePath = new File("D:/ANewFile");
		if(!savePath.exists()){
			savePath.mkdirs();
		}
		
		//设置缓冲文件夹
		File tempPath = new File("D:/ANewFileTemp");
		if(!tempPath.exists()){
			tempPath.mkdirs();
		}
		
		//判断form表单中是否含有enctype属性
		if(ServletFileUpload.isMultipartContent(req)){
			
			//设置缓冲文件属性
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(1024);
			factory.setRepository(tempPath);
			
			//设置上传文件的大小
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
		
		//创建保存文件夹
		File savePath = new File("D:/ANewFile");
		if(!savePath.exists()){
			savePath.mkdirs();
		}
		
		//创建缓存文件夹
		File tempPath = new File("D:/ANewFileTemp");
		if(!tempPath.exists()){
			tempPath.mkdirs();
		}
		
		//判断form表单是否含有enctype属性
		if(ServletFileUpload.isMultipartContent(req)){
			
			//设置缓冲区
			DiskFileItemFactory itemFactory = new DiskFileItemFactory();
			itemFactory.setSizeThreshold(1024);  //设置缓冲区大小
			itemFactory.setRepository(tempPath);  //设置缓冲文件夹
			
			//设置文件上传
			ServletFileUpload servletFileUpload = new ServletFileUpload(itemFactory);
			servletFileUpload.setFileSizeMax(1024*1024*100);  //设定上传文件的大小
			
			try{
				List<FileItem> itemList = servletFileUpload.parseRequest(req);
				for(FileItem item : itemList){
					if(item.isFormField()){
						//TODO 为什么这部分也会输出
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
		//1.文件上传后的存放路径
		File savePath = new File("D:/ANewFile");
		if(!savePath.exists()){
			savePath.mkdirs();
		}
		
		//2.设置文件上传的临时路径
		File tempPath = new File("D:/ANewFileTemp");
		if(!tempPath.exists()){
			tempPath.mkdirs();
		}
		
		//3.判断表单是否含有enctype属性
		if(ServletFileUpload.isMultipartContent(req)){
			
			//为磁盘文件创建一个工厂
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(1024);  //缓冲区大小
			factory.setRepository(tempPath);  //临时文件路径
			
			//创建上传文件的处理工厂
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setSizeMax(1024*1024*20);  //上传文件的最大体积
			
			try{
				List<FileItem> itemList = upload.parseRequest(req);
				for(FileItem item : itemList){
					
					if(item.isFormField()){
						//普通元素
						System.out.println("FieldName"+item.getFieldName());
						System.out.println("getString"+item.getString());
					}else{
						//文件元素
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
						
						System.out.println(item.getName() + "  -> 文件上传成功！");
						req.getRequestDispatcher("/WEB-INF/views/upload.jsp").forward(req,resp);
					}
				}
			}catch(FileUploadException e) {
                e.printStackTrace();
			}
				
			
		}else{
			throw new RuntimeException("form表单的enctype属性错误");
		}*/
		
		
	}


