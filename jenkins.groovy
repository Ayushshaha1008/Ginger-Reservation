pipeline {
    agent any
    stages{
        stage('pull'){ // Here in this stage the code will be copied 
            steps{
              git branch: 'dev', url: 'https://github.com/Ayushshaha1008/Ginger-Reservation.git' 
            }
        }
         stage('build'){ //Here in this stage the code will be build and as a result we get a /target directory 
            steps{
                sh 'mvn clean package'
            }
        }
         stage('test'){ //Here the build will be tested by the sonarqube 
            steps{
                withSonarQubeEnv(installationName: 'sonar', credentialsId: 'sonar-cred'){
                sh 'mvn sonar:sonar -Dsonar.projectKey=ginger-reservation'
                }
            }
        }
         stage('Quality gate'){ //Here the pipleline will wait for specific time as it is specified in the code
            steps{
                timeout(time: 100, unit: 'SECONDS'){
                    waitForQualityGate abortPipeline: true, credentialsId: 'sonar-cred'
                } 
            }
        }
         stage('docker-build'){ //Here we will write dockerfile which will get converted into the image 
            steps{
                sh 'docker build -t ayushshaha1008/ginger-reservation-app:latest .'
            }
         }
         stage('docker-push'){ //Here the created image will get pushed to the dockerhub 
            steps{
                sh 'docker push ayushshaha1008/ginger-reservation-app:latest'
            }
         }
         stage('Deploy'){ //Here all the yaml file that are specified in the yaml folder will get executed 
            steps{
                sh 'kubectl apply -f yaml/'
            }
        }
    }
}