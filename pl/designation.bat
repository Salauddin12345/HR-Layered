@echo off
path=c:\windows;c:\windows\system32;c:\mingw32\bin;c:\jdk14.0.2\bin
cd\
cd javaprojects\hr\pl
java -classpath ..\common\dist\hr-common.jar;..\dbdl\build\libs\dbdl.jar;..\bl\build\libs\bl.jar;build\libs\pl.jar;c:\itext7\*;c:\mysqljar\*;. com.thinking.machines.hr.pl.Main
