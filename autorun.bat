rem …Ë÷√tomcat¬∑æ∂
set web=D:\apache-tomcat-7.0.55\webapps\
set bin=D:\apache-tomcat-7.0.55\bin\


set old=%cd%
cd %bin%
call %bin%shutdown.bat

rd /s /Q "%web%ROOT"
del "%web%*.war" 
cd %old%
xcopy ".\target\*.war"  "%web%"
rename "%web%ace*.war" "ROOT.war"

cd %bin%
call %bin%catalina.bat run

cd %old%


