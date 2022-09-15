cd /opt/tomcat/webapps/pastebin/WEB-INF/code
export CLASSPATH=.:/opt/tomcat/lib/servlet-api.jar:../
export classes=/opt/tomcat/webapps/pastebin/WEB-INF/classes
rm -rf $classes/*

cd dto
javac *.java;
javac -d . *.java;
mv ./dto $classes
cd ..

cd dao
javac *.java;
javac -d . *.java;
mv ./dao $classes
cd ..

cd helpers
javac *.java;
javac -d . *.java;
mv ./helpers $classes
cd ..

cd services
javac *.java;
javac -d . *.java;
mv ./services $classes
cd ..

cd controllers
javac *.java;
javac -d . *.java;
mv ./controllers $classes
cd ..

systemctl restart tomcat