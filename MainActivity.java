package com.example.newsclient;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.jit.lib.SmartImageView;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	private ListView lv;
	private List<News> lists;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		lv = (ListView) findViewById(R.id.lv);
		// 去服务器取数据
		initData();

	}

	private void initData() {
		new Thread() {
			public void run() {
				String path = "http://169.254.249.1:8080/news.xml";
				try {
					URL url = new URL(path);
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setRequestMethod("GET");
					conn.setConnectTimeout(5000);
					int code = conn.getResponseCode();
					if (code == 200) {
						InputStream in = conn.getInputStream();
						// 解析xml
						lists = XmlParserUtils.xmlParser(in);
						runOnUiThread(new Runnable() {
							public void run() {
								lv.setAdapter(new MyAdapter());	
							}
						});
						
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
		}.start();

	}

	public class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return lists.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view;
			if (convertView == null) {
				view = View.inflate(getApplicationContext(), R.layout.item, null);
			} else {
				view = convertView;
			}
			News news = lists.get(position);
			TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
			TextView tv_desc = (TextView) view.findViewById(R.id.tv_desc);
			TextView tv_type = (TextView) view.findViewById(R.id.tv_type);
			SmartImageView smartImageView = (SmartImageView) view.findViewById(R.id.iv);
			smartImageView.setImageUrl(news.getImage());
			String title = news.getTitle();
			tv_title.setText(title);
			
			tv_desc.setText(news.getDescription());
			switch (Integer.parseInt(news.getType())) {
			case 1:
				tv_type.setText(news.getComments()+"跟帖");
				break;
			case 2:
				tv_type.setText(news.getComments()+"国内");
				break;
			case 3:
				tv_type.setText(news.getComments()+"国际");
				break;
			}
			return view;
		}

	}

}
