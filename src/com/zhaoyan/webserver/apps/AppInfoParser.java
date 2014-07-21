package com.zhaoyan.webserver.apps;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class AppInfoParser {

	public AppInfoParser() {
	}

	public List<AppInfo> parseAppInfo(String xmlFolderh) {
		List<AppInfo> list = new ArrayList<>();
		File dirFile = new File(xmlFolderh);
		File[] files = dirFile.listFiles();
		
		AppInfo appInfo = null;
		for(File file : files){
			if (file.getName().endsWith("xml")) {
				appInfo = loadFromFile(file.getAbsolutePath());
				System.out.println("appinfo:" + appInfo.getLabel());
				list.add(appInfo);
			}
		}
		
		System.out.println("appinfo.size=" + list.size());
		return list;
	}

	public AppInfo loadFromFile(String path) {
		AppInfo appInfo =  new AppInfo();
		File file = new File(path);

		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser;
		try {
			parser = factory.newSAXParser();
			parser.parse(file, new MyHandler(appInfo));
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return appInfo;
	}

	class MyHandler extends DefaultHandler {
		private Stack<String> stack = new Stack<String>();  
		
		private AppInfo appinfo = null;
		
		public MyHandler(AppInfo appInfo){
			this.appinfo = appInfo;
		}
		
		@Override
		public void startDocument() throws SAXException {
			System.out.println("parse began");
		}

		@Override
		public void endDocument() throws SAXException {
			System.out.println("parse finished");
		}

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			stack.push(qName);
		}
		
		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			 String theString = new String(ch, start, length);  
			String tag = stack.peek();
//			System.out.println("tag:" + tag);
			if ("app_id".equals(tag)) {
				int appid = Integer.parseInt(theString);
				appinfo.setAppId(appid);
			} else if ("app_label".equals(tag)) {
				appinfo.setLabel(theString);
			} else if ("author_id".equals(tag)) {
				int authorId = Integer.parseInt(theString);
				appinfo.setAuthorId(authorId);
			} else if ("author".equals(tag)) {
				appinfo.setAuthor(theString);
			} else if ("update_time".equals(tag)) {
				appinfo.setDate(theString);
			} else if ("app_language".equals(tag)) {
				appinfo.setAppLanguage(theString);
			} else if ("app_version".equals(tag)) {
				appinfo.setAppVersion(theString);
			} else if ("introduce".equals(tag)) {
				appinfo.setIntroduce(theString);
			} else if ("title".equals(tag)) {
				appinfo.setTitle(theString);
			} else if ("gold_infos".equals(tag)) {
				appinfo.setNotes(theString);
			} else if ("size".equals(tag)) {
				long size = Long.parseLong(theString);
				appinfo.setSize(size);
			} else if ("app_url".equals(tag)) {
				appinfo.setAppUrl(theString);
			} else if ("icon_url".equals(tag)) {
				appinfo.setIconUrl(theString);
			} else if ("jiemian_url1".equals(tag)) {
				appinfo.setJiemianUrl1(theString);
			} else if ("jiemian_url2".equals(tag)) {
				appinfo.setJiemianUrl2(theString);
			} else if ("app_type".equals(tag)) {
				appinfo.setAppType(theString);
			} else if ("package_name".equals(tag)) {
				appinfo.setPackageName(theString);
			} else {
//				System.out.println("error:" + tag);
			}
//			System.out.println(theString);
		}

		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			stack.pop();
		}
	}

}
