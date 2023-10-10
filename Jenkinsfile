@Library('pipeline-library')_



pipeline {
    agent any
    
	environment {
        final testFolder = "testCase"
        final mail = "ab22213395@yahoo.com.tw"
        final jobName = "${currentBuild.fullDisplayName}"
        final karaterReport = "${env.WORKSPACE}/${testFolder}/target/karate-reports"
        final report = "${env.WORKSPACE}/report"
        final emailTemplate = "${JENKINS_HOME}/email-templates"
        final dockerPort = "8085"
        final url = env.JENKINS_URL.replaceAll("8080", dockerPort);
        final imageName = "env"
        final dockerName = "api"
        final apiRep = "git@github.com:aidenguo1997/BookStore.git"
        final testCaseRep = "git@github.com:aidenguo1997/Test_Case.git"
        final swaggerURL = "${url}v3/api-docs"
        apiUrl = "${url}swagger-ui/index.html"
	}
	
    stages {
        stage('Check version') {
            steps {
                script {
                    dir(dockerName) {
                        try {
                            git branch: 'main', credentialsId: 'github', url: env.apiRep
                            devVer = sh(returnStdout:true, script: "git tag --sort=-creatordate | head -n 1")
                            devVer = devVer.trim()
                            println "devVer: ${devVer}"
                        }
                        catch (exc) {
                            error "Develop API repository is not exist."
                        }
                    }
                    dir(testFolder) {
                        try {
                            git branch: 'main', credentialsId: 'github', url: env.testCaseRep
                            testVer = sh(returnStdout:true, script: "git tag --sort=-creatordate | head -n 1")
                            testVer = testVer.trim()
                            println "testVer: ${testVer}"
                            listTestVer = devFunction.getVers()
                            println "listTestVer: ${listTestVer}"
                        }
                        catch (exc) {
                            error "Test case repository is not exist."
                        }
                    }
                    testVers = devFunction.getTestVers(devVer, listTestVer)
                }
            }
        } 
        stage('Run API') {
            steps {
                script {;
                    dir('api') {
                        image = sh(returnStdout:  true, script: "docker images -q ${imageName}").trim()
                        if (image == ""){
                            println "*** Star to build docker image ***"
                            try {
                                git branch: 'main', credentialsId: 'github', url: env.apiRep
                                sh "mvn clean package"
                                sh "docker build -t env ."
                            }
                            catch (exc) {
                                error "Build docker image fail."
                            }
                        }
                        try {
                            sh "docker run -d -p ${dockerPort}:${dockerPort} --name ${dockerName} ${imageName}"
                        }
                        catch (exc) {
                            error "Run docker fail."
                        }
                    }
                }
            }
        }
        stage('Check Spring boot is running') {
            steps {
                script {
                    for (i=0; i<3; i++){
                        try {
                            status = sh(returnStdout:  true, script: "curl -I -o /dev/null -s -w %{http_code} ${swaggerURL}")
                            println status
                            if (status == "200"){
                                break
                            }
                        }
                        catch (exc) {
                            println "Retry ${i+1}"
                            println "Please waitting for run API."
                            sleep 10
                        }
                        if (i == 2){
                            error "Running api docker fail."
                        }
                    }
                }
            }
        }
        stage('test') {
            steps {
                script {
                    dir(testFolder) {
                        println "testVers: " + testVers
                        devFunction.getFinalResult(devVer, testVers)
                        def resultJson = readJSON file: "${report}/result.json"
                        if (resultJson[1].scenariosfailed != "0"){
                            error "Test fail."
                        }
                    }
                }
            }
        }
    }
    post {
        always {
            script{
                devFunction.stopDocker();
            }
            publishHTML (target : [allowMissing: false,
                alwaysLinkToLastBuild: true,
                keepAll: true,
                reportDir: 'report',
                reportFiles: 'timeline.html',
                reportName: 'Reports',
                reportTitles: 'The Report'])
                    emailext body: '''${SCRIPT, template="groovy-email-html.template"}''',
                    mimeType: 'text/html',
                    subject: "[Develop API testing] ${currentBuild.fullDisplayName} - ${currentBuild.currentResult}",
                    to: "${mail}"
            script{
                baseFunction.removeFolder();
            }
        }
    }
}
