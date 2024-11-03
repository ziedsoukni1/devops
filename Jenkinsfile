pipeline{
    agent any



    stages {


        stage('Getting project from Git') {
            steps{
      			checkout([$class: 'GitSCM', branches: [[name: '*/main']],
			extensions: [],
			userRemoteConfigs: [[url: 'https://github.com/ziedsoukni1/devops.git']]])
            }
        }


       stage('Cleaning the project') {
            steps{
                	sh "mvn -B -DskipTests clean  "

            }
        }






        //  stage('Unit Tests') {
        //     steps{
        //        		 sh "mvn test "
        //     }
        // }




                 stage('Code Quality Check via SonarQube') {
                   steps{

       sh " mvn clean verify sonar:sonar -Dsonar.projectKey=powerdevops -Dsonar.projectName='powerdevops' -Dsonar.host.url=http://192.168.50.4:9000 -Dsonar.token=sqp_eb5069daebacecb6859a81e7d566f9ae524ab61d"
                   }
               }


                stage('Publish to Nexus') {
                   steps {



         sh 'mvn clean package deploy:deploy-file -DgroupId=com.esprit.examen -DartifactId=achat -Dversion=1.0 -DgeneratePom=true -Dpackaging=jar -DrepositoryId=deploymentRepo -Durl=http://192.168.50.4:8081/repository/maven-releases/ -Dfile=target/achat-1.0.jar'



                   }
               }

 stage('Build Docker Image') {
                      steps {
                          script {
                            sh 'docker build -t ziedsoukni/devops:back .'
                          }
                      }
                  }

                  stage('login dockerhub') {
                                        steps {

				sh 'docker login -u ziedsoukni --password dckr_pat_-DG6XRACAlHBv3SPOuCdwsAgh80'
                                            }
		  }

	                      stage('Push Docker Image') {
                                        steps {
                                   sh 'docker push ziedsoukni/devops:back'
                                            }
		  }




		   stage('Run Spring && MySQL Containers yes') {
               steps {
                   script {
                       sh 'docker-compose up '

                   }
               }
           }







}


        


}