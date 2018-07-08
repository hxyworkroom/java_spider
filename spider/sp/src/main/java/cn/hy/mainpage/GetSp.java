package cn.hy.mainpage;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;


/**
 * @author parasoftCD
 *
 */
public class GetSp {
	/**
	 * @author hy
	 * @param args
	 * @throws Exception
	 * 
	 * 1.这是爬取优酷的
	 * 2.首先抓包  找到实际mp4视屏地址  http://117.177.248.139/6774EC36FA33D82B3B8  就是这个
	 * 3.在fiddler中提取reffer的地址  添加进来
	 * 4.修改浏览器报头
	 * 5.还是采用IO读写的方式处理
	 * 6.优酷的视屏  是分成了一段一段的   需要抓取多个
	 */
	public static void main(String[] args) throws Exception {
		File file=new File("D:\\a.mp4");
		file.createNewFile();
		String url = "http://117.177.248.139/6774EC36FA33D82B3B871B62B5/03000A01005B343E6BDAECC13284A6D16F9ACE-E0D3-412E-D173-EB66C4491896.mp4.ts?ccode=0519&duration=64&expire=18000&psid=084d8f915834415eafe9abb861c0b2e9&sp=&ups_client_netip=df57dc73&ups_ts=1530693598&ups_userid=&utid=tzZcE3UuzCkCAd9X3HMU6SJi&vid=XMzY4NzI1MTE4MA%3D%3D&s=efbfbdefbfbd024eefbf&vkey=B84f5d18d9d4bd4ba9ea04312d7b65bf5&ts_start=11.9&ts_end=17.1&ts_seg_no=2&ts_keyframe=1";
		URL imgurl = new URL(url);
		URLConnection connection = imgurl.openConnection();
		connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36");
		connection.setRequestProperty("Referer","http://www.youku.com/");
		connection.setConnectTimeout(10 * 1000);
		
		InputStream in = connection.getInputStream();
		BufferedOutputStream out = new BufferedOutputStream(
				new FileOutputStream("D:\\a.mp4"));
		byte[] buf = new byte[1024*1024];
//		System.out.println(buf.length);
		int size;
		long jd = 1;
		long st = (long) System.currentTimeMillis();
		while (-1 != (size = in.read(buf))) {
//			System.out.println(size+"aaaaa");
			out.write(buf, 0, size);			//目前只能每次写入16kb速度比较慢
			System.out.println(buf.length);
			System.out.println("正在下载中-写入次数："+jd+"/次");
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
