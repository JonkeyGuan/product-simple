cd ../
mvn clean package -DskipTests

cd env
oc start-build product-simple --from-dir=../target/ -F
