package cn.hy.mainpage;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class GetSp4 {
	/**
	 * @author hy
	 * @param args
	 * @throws Exception
	 * 
	 * 爱奇艺电影  vip
	 * http://117.139.18.142/videos/other/20180606/2c/bd/d9fe7dd988821e72b51e096f864df204.f4v
	 * 上面这个请求，类似的，都是片头广告
	 */
	public static void main(String[] args) throws Exception {
		File file=new File("D:\\a.f4v");
		file.createNewFile();
		String url = "http://122.72.14.103/videos/v0/20171206/7b/d2/66685152ce7c27c0cb02beccf4c54776.f4v?key=0fcc9acf354dd4db640c676737f3feb4a&dis_k=2a9d262a78c50ec6b2e2604a0655d2229&dis_t=1530776206&dis_dz=CMNET-SiChuan&dis_st=42&src=iqiyi.com&uuid=df57dc73-5b3dca8e-101&rn=1530776225643&qd_tm=1530776179546&qd_tvid=861741400&qd_vipdyn=0&qd_k=c5aa02ce360f25138efd2d12f041a9f0&cross-domain=1&qd_aid=861741400&qd_uid=&qd_stert=0&qypid=861741400_02020031010000000000&qd_p=df57dc73&qd_src=01010031010000000000&qd_index=1&qd_vip=0&qyid=64df6a1db723c2d20a4ecbb46562f633&pv=0.1&qd_vipres=0&range=2304000-10833919";
		URL imgurl = new URL(url);
		URLConnection connection = imgurl.openConnection();
		connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99");
		connection.setRequestProperty("Referer","http://www.iqiyi.com/v_19rre6z8z8.html");
		connection.setConnectTimeout(10 * 1000);
		
		InputStream in = connection.getInputStream();
		BufferedOutputStream out = new BufferedOutputStream(
				new FileOutputStream("D:\\a.f4v"));
		byte[] buf = new byte[1024*1024];
		int size;
		long jd = 1;
		long dx = 0;
		long st = (long) System.currentTimeMillis();
		while (-1 != (size = in.read(buf))) {
			System.out.println("size: "+size);
			out.write(buf, 0, size);			//目前只能每次写入16kb速度比较慢
			System.out.println("正在下载中-写入次数："+jd+"/次");
			System.out.println("已下载中：");
			Thread.sleep(1500);
			dx = dx + size;
			jd++;
		}
		long et = (long) System.currentTimeMillis();
		int ys = ((int) (et - st))/1000;
		System.out.println("下载完毕  总计用时："+ ys +"-s"+"文件大小是："+dx);
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
