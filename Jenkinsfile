pipeline {
    agent any
    tools{
        maven 'maven'
    }
    stages{
        stage('Build Maven'){
            steps{
                checkout([$class: 'GitSCM', branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/rihab1478/devops-automation']]])

                sh 'mvn clean install'
            }
        }
        stage('Build docker image'){
            steps{
                script{
                    sh 'docker build  -t rihab23/devops-integration . '
                }
            }
        }
        stage('Push image to Hub'){
            steps{
                script{
                   withCredentials([string(credentialsId: 'rihab23', variable: 'dockerpwd')]) {
                   sh 'docker login -u rihab23 -p ${dockerpwd}'
}
                   sh 'docker push rihab23/devops-integration'
                }
            }
        }

    }
}