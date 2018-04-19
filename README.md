# INF1416 - Trabalho 3 de Segurança

Instalar o JDBC

http://www.sqlitetutorial.net/sqlite-java/sqlite-jdbc-driver/

Adicionar no buildpath do projeto o driver e depois no StartConnection.java modificar:

			connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/tassi/eclipse-workspace/TrabLab_3/segurancaBD");

Modificar para aonde está o arquio segurancaDB
