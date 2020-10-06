package cn.e3mall.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

public class GeneratorTest {
	public static void main(String[] args) throws Exception {
		List<String> warnings=new ArrayList<String>();
		boolean overwrite=true;
		File config=new File("generatorConfig.xml");
		ConfigurationParser cp=new ConfigurationParser(warnings);
		Configuration configs=cp.parseConfiguration(config);
		DefaultShellCallback callback=new DefaultShellCallback(overwrite);
		MyBatisGenerator myBatisGenerator=new MyBatisGenerator(configs, callback, warnings);
		myBatisGenerator.generate(null);
		System.out.println("逆向生成成功！");
	}
}
