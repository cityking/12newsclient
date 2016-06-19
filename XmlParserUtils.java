package com.example.newsclient;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import android.util.Xml;

public class XmlParserUtils {
	@SuppressWarnings("null")
	public static List<News> xmlParser(InputStream in) {

		List<News> lists = null;
		News news = null;
		XmlPullParser parser = Xml.newPullParser();
		try {
			parser.setInput(in, "utf-8");
			int type = parser.getEventType();
			while (type != XmlPullParser.END_DOCUMENT) {
				
				switch (type) {
				case XmlPullParser.START_TAG:
					if("channel".equals(parser.getName())){
						lists = new ArrayList<News>();
					}else if("item".equals(parser.getName())){
						 news = new News();
					}else if ("title".equals(parser.getName())) {
					
						news.setTitle(parser.nextText());
						
					}else if ("image".equals(parser.getName())){
						news.setImage(parser.nextText());
						
					}else if ("type".equals(parser.getName())){
						news.setType(parser.nextText());
					}else if ("description".equals(parser.getName())){
						news.setDescription(parser.nextText());
					}else if ("comments".equals(parser.getName())){
						news.setComments(parser.nextText());
					}
					break;
				case XmlPullParser.END_TAG:
					if("item".equals(parser.getName())){
						lists.add(news);
					}
					break;
				}
				type = parser.next();

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return lists;

	}
}
