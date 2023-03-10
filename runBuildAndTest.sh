# CONSTRUINDO O COTUBA.

cd ./cotuba/;
mvn clean install;
unzip -o target/cotuba-cli-0.0.1-SNAPSHOT-distribution.zip -d ../;
cd ..;

# CONSTRUINDO O PLUGIN

cd ./tema-paradizo;
mvn clean package;
cp target/tema-paradizo-0.0.1-SNAPSHOT.jar ../libs/;
cd ..;
cp ~/.m2/repository/org/jsoup/jsoup/1.11.2/jsoup-1.11.2.jar ./libs/;

# TESTANDO

./cotuba.sh -d ./cotuba/livro-exemplo -f pdf;