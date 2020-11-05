package cn.e3mall.item.test;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import cn.e3mall.item.pojo.Student;
import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * @Description:
 * @version 1.0
 * @since 
 * @author Administrator
 * @company 
 * @copyright 
 * @date 2020年11月4日 下午8:17:24
 */
public class FreeMakerTest {

	@Test
	public void testFreeMarker() throws Exception{
		//创建一个模板文件
		//创建一个configuration对象
		Configuration configuration = new Configuration(Configuration.getVersion());
		//设置模板文件保存的目录
		configuration.setDirectoryForTemplateLoading(new File("E:/rope/SSMShoping/e3-item-web/src/main/webapp/WEB-INF/ftl"));
		//模板文件的编码格式，一般为UTF-8
		configuration.setDefaultEncoding("UTF-8");
		//加载一个模板文件，创建一个模板对象
		//Template template = configuration.getTemplate("hello.ftl");
		Template template = configuration.getTemplate("student.ftl");
		//创建一个数据集，可以是pojo也可以是map，推荐使用map
		Map data=new HashMap<>();
		data.put("heelo", "hello world!");
		//创建一个pojo对象
		Student student = new Student(1, "小明", 18, "回龙观");
		data.put("student", student);
		//添加一个list
		List<Student> stuList = new ArrayList<>();
		stuList.add(new Student(1, "小明1", 18, "回龙观"));
		stuList.add(new Student(2, "小明2", 19, "回龙观"));
		stuList.add(new Student(3, "小明3", 20, "回龙观"));
		stuList.add(new Student(4, "小明4", 21, "回龙观"));
		stuList.add(new Student(5, "小明5", 22, "回龙观"));
		stuList.add(new Student(6, "小明6", 23, "回龙观"));
		stuList.add(new Student(7, "小明7", 24, "回龙观"));
		stuList.add(new Student(8, "小明8", 25, "回龙观"));
		stuList.add(new Student(9, "小明9", 26, "回龙观"));
		data.put("stuList", stuList);
		//添加日期类型
		data.put("date", new Date());
		//null值的测试
		data.put("val", "123");
		//创建一个Writer对象，指定输出文件的路径及文件名
		//FileWriter out = new FileWriter(new File("E:/rope/SSMShoping/e3-freemarker/src/freemarker/hello.txt"));
		FileWriter out = new FileWriter(new File("E:/rope/SSMShoping/e3-freemarker/src/freemarker/student.html"));
		//生成静态页面
		template.process(data, out);
		//关闭流
		out.close();
	}
}
