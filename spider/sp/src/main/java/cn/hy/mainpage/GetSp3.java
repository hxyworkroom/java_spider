package cn.hy.mainpage;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class GetSp3 {
	/**
	 * @author hy
	 * @param args
	 * @throws Exception
	 * 
	 * 下载网易云歌曲
	 * 告别vip
	 * 随便下
	 */
	public static void main(String[] args) throws Exception {
		File file=new File("D:\\gequ.mp3");
		file.createNewFile();
		String url = "http://m10.music.126.net/20180705143048/27aa49b78fa55b4fe8b9e7873187658f/ymusic/ee89/f9f8/c4f9/9c427875c66f0ab96776ebf5e6ebf077.mp3";
		URL imgurl = new URL(url);
		URLConnection connection = imgurl.openConnection();
		connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99");
		connection.setRequestProperty("Referer","https://music.163.com/#/song?id=439076364");
		connection.setConnectTimeout(10 * 1000);
		
		InputStream in = connection.getInputStream();
		BufferedOutputStream out = new BufferedOutputStream(
				new FileOutputStream("D:\\gequ.mp3"));
		byte[] buf = new byte[1024*1024];
		int size;
		long jd = 1;
		long st = (long) System.currentTimeMillis();
		while (-1 != (size = in.read(buf))) {
			System.out.println("size: "+size);
			out.write(buf, 0, size);			//目前只能每次写入16kb速度比较慢
			System.out.println("正在下载中-写入次数："+jd+"/次");
			System.out.println("已下载中："+jd*16493+"/kb");
//			Thread.sleep(100000);
			jd++;
		}
		long et = (long) System.currentTimeMillis();
		int ys = ((int) (et - st))/1000;
		System.out.println("下载完毕  总计用时："+ ys +"-s");
		try {
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out = null;
		}
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			in = null;
		}

	}
}
