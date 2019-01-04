## Java版本的公章生成使用说明

##### 使用Canvas生成公章项目地址：https://github.com/niezhiliang/canvas-draw-seal

该项目已经发布到Maven中央仓库，使用只需要在pom文件中依赖就好

```java
    <dependency>
        <groupId>com.niezhiliang.signature.utils</groupId>
        <artifactId>signature-utils</artifactId>
        <version>1.1.0</version>
    </dependency>

```
公章环境配置

- 支持字体：`{宋体，方正黑体，楷体}`

- 支持颜色：`{红色，蓝色，黑体}`

#### 圆形公章

```java
  /*
   * companyName：签章文字 签章主文字 默认字体大小30 如果输入文字多余15字 则字体会自动缩小为23
   * color：公章及字体颜色 0.红色 1.蓝色 2.黑色 不是传入012 则默认红色
   * font：公章字体 0.宋体 1.方正黑体 2. 楷体  不是传入012 则默认宋体
   * serNo 签章的下弦文 公章编号 效果最佳：3-13位，如果不需要绘制下弦文则传null 或者 ""
   * title 签章副文字，如果不需要绘制下弦文则传null 或者 "" 最多八个字符，多于八个字符会覆盖掉主文字
   * 
   * return :返回是生成签章图片的base64
   **/
    String base64 = SealUtils.companyCircleSeal("浙江杭州江干下沙某某某网络集团有限公司",0,0,"1234567899876","合同专用");
```

![演示图片](https://github.com/niezhiliang/signature-utils/blob/master/imgs/%E9%BB%91%E4%BD%93.png)

![演示图片](https://github.com/niezhiliang/signature-utils/blob/master/imgs/%E6%A5%B7%E4%BD%93.png)

![演示图片](https://github.com/niezhiliang/signature-utils/blob/master/imgs/%E6%96%B9%E6%AD%A3%E9%BB%91%E4%BD%93.png)

要是出现上面这种情况，字体全部都是框框，不用担心，这个是服务器环境不支持该字体，解决办法：
在自己本地电脑上找到该字体，将其放在Java jre/lib/fonts的目录下 支持的三种字体我只找到其中两种放在项目的fonts目录下面 还有一个方正黑体没找到，方正黑体字体不是我想搞得，是该死的产品要求的字体，我没得办法啦。
想用就得你自己去找该字体啦。

例如：我电脑的放置路径就是 `/java/jdk1.8/jre/lib/fonts`

字体加上应该就没啥问题啦！

#### 椭圆公章
```java
  /*
   * companyName：签章文字 签章主文字 默认字体大小30 如果输入文字多余15字 则字体会自动缩小为23
   * color：公章及字体颜色 0.红色 1.蓝色 2.黑色 不是传入012 则默认红色
   * font：公章字体 0.宋体 1.方正黑体 2. 楷体  不是传入012 则默认宋体
   * serNo 签章的下弦文 公章编号 效果最佳：3-13位，如果不需要绘制下弦文则传null 或者 ""
   * title 签章副文字，如果不需要绘制下弦文则传null 或者 "" 最多八个字符，多于八个字符会覆盖掉主文字
   * 
   * return :返回是生成签章图片的base64
   **/
    String base64 = SealUtils.companyEllipseSeal("浙江某某某网络集团有限公司",0,0,"1234567899876","XX专用");
```
![演示图片](https://github.com/niezhiliang/signature-utils/blob/master/imgs/%E5%AE%8B%E4%BD%93.png)




