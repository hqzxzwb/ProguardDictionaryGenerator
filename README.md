# ProguardDictionaryGenerator
一种生成proguard汉字字典的算法

为了保护我们的JAVA/Android代码,我们常使用proguard对代码进行混淆([http://proguard.sourceforge.net/](http://proguard.sourceforge.net/))。

默认情况下,proguard混淆时将类/变量/方法重命名为a,b,c,...的格式。

我们可以通过配置`-obfuscationdictionary`,`-classobfuscationdictionary`和`-packageobfuscationdictionary`分别指定变量/方法名、类名、包名混淆后的字符串集。

如果使用复杂的汉字,我们能将代码的阅读难度再提高一个档次。

本程序给出了一种生成生僻汉字字符集的不精确算法。