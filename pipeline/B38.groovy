pipeline {
    agent{
        label 'dummy'
    } 
    stages{
        stage (pull){
            steps{
                echo "we are pulling from github"
                git "https://github.com/AnupDudhe/studentapp-ui"
            }   
        }
        stage (build){
            steps{
                sh '''
                sudo apt update
                sudo apt install maven -y
                sudo apt install unzip -y
                sudo curl -O https://dlcdn.apache.org/tomcat/tomcat-9/v9.0.96/bin/apache-tomcat-9.0.96.zip
                sudo unzip apache-tomcat-9.0.96.zip
                sudo mvn clean package
                
                '''
                echo "we are building"
            }   
        }
        stage (configure){
            steps{
                sh '''
                sudo mv target/*.war  apache-tomcat-9.0.96/webapps/student.war
                sudo bash apache-tomcat-9.0.96/bin/catalina.sh start
                '''
                echo "we are configuring"
            }   
        }
    }
}
