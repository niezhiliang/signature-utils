## Java版本的公章生成使用说明

##### 使用Canvas生成公章项目地址：https://github.com/niezhiliang/canvas-draw-seal

该项目已经发布到Maven中央仓库，使用只需要在pom文件中依赖就好

```xml
linux 系统使用版本2.0.0签章五角星位置是正确的， mac 2.0.1五角星位置是正确的
<dependency>
    <groupId>com.niezhiliang.signature.utils</groupId>
    <artifactId>signature-utils</artifactId>
    <version>2.0.1</version>
</dependency>
```
公章环境配置

- 支持字体：`{宋体，方正黑体，楷体}`

- 支持颜色：`{红色，蓝色，黑体}`

#### 圆形公章


| 名称   | 类型 | 是否必须| 参数描述
| :----: | :---: | :---: | :---:
| companyName  |String|  必须  |  公司名称 默认字体大小30 如果输入文字多余15字 则字体会自动缩小为23
| color  |int|  可选  |   签章字体颜色，0:红色，1:蓝色 2:黑色 ,不传默认为0，
| font  |int|  必须  |   签章字体，0:宋体，1:方正黑体 2:楷体 ,不传默认为0，
| serNo  |String|  可选  |   签章的下弦文，不填不生成下弦文  效果最佳：3-13位
| title  |String|  可选  |   签章的副标题（财务专用章）,不填不生成副标题 最多八个字符，多于八个字符会覆盖掉主文字


```java
SealDTO sealDTO = new SealDTO();
sealDTO.setCompanyName("阿里云计算有限公司");
sealDTO.setSerNo("123456789987");
sealDTO.setTitle("财务专用章");
BaseSeal baseSeal =  new CircleSealFactory().getInstance();
System.out.println(baseSeal.createSeal(sealDTO));
```

![演示图片](https://suyu-img.oss-cn-shenzhen.aliyuncs.com/%E9%BB%91%E4%BD%93.png)

![演示图片](https://suyu-img.oss-cn-shenzhen.aliyuncs.com/%E6%A5%B7%E4%BD%93.png)

![演示图片](https://suyu-img.oss-cn-shenzhen.aliyuncs.com/%E6%96%B9%E6%AD%A3%E9%BB%91%E4%BD%93.png)

要是出现上面这种情况，字体全部都是框框，不用担心，这个是服务器环境不支持该字体，解决办法：
在自己本地电脑上找到该字体，将其放在Java jre/lib/fonts的目录下 支持的三种字体我只找到其中两种放在项目的fonts目录下面 还有一个方正黑体没找到，方正黑体字体不是我想搞得，是该死的产品要求的字体，我没得办法啦。
想用就得你自己去找该字体啦。

例如：我电脑的放置路径就是 `/java/jdk1.8/jre/lib/fonts`

字体加上应该就没啥问题啦！

#### 椭圆公章


| 名称   | 类型 | 是否必须| 参数描述
| :----: | :---: | :---: | :---:
| companyName  |String|  必须  |  公司名称 默认字体大小30 如果输入文字多余15字 则字体会自动缩小为23
| color  |int|  可选  |   签章字体颜色，0:红色，1:蓝色 2:黑色 ,不传默认为0，
| font  |int|  必须  |   签章字体，0:宋体，1:方正黑体 2:楷体 ,不传默认为0，
| serNo  |String|  可选  |   签章的下弦文，不填不生成下弦文  效果最佳：3-13位
| title  |String|  可选  |   签章的副标题（财务专用章）,不填不生成副标题 最多八个字符，多于八个字符会覆盖掉主文字

```java
SealDTO sealDTO = new SealDTO();
sealDTO.setCompanyName("阿里云计算有限公司");
sealDTO.setSerNo("123456789987");
sealDTO.setTitle("财务专用章");
BaseSeal baseSeal =  new EllipseSealFactory().getInstance();
System.out.println(baseSeal.createSeal(sealDTO));
```
![演示图片](https://suyu-img.oss-cn-shenzhen.aliyuncs.com/%E5%AE%8B%E4%BD%93.png)




