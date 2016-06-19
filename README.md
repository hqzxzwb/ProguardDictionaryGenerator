# ProguardDictionaryGenerator
一种生成proguard字典的算法

为了保护我们的JAVA/Android代码,我们常使用proguard对代码进行混淆([http://proguard.sourceforge.net/](http://proguard.sourceforge.net/))。

默认情况下,proguard混淆时将类/变量/方法重命名为a,b,c,...的格式。

我们可以通过配置`-obfuscationdictionary`,`-classobfuscationdictionary`和`-packageobfuscationdictionary`分别指定变量/方法名、类名、包名混淆后的字符串集。

一开始我想,把字典改成复杂汉字集合可以制造更多的阅读障碍,就写了一个粗略的算法对汉字复杂程度进行了一个排序。

后来我发现,只要用双字节字符,都可以作为变量名。就选取了unicode 0x0100-0x10FF的字符来进行排序,并把排序顺序反过来,从不复杂到复杂。
这样不复杂的字符都特别小而相似,而且还有很多字符其实不存在或者没有被字体库包含,效果更好了。