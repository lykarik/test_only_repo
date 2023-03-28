pipeline {
  agent {label 'master'}

  stages {
    stage ('some commands') {
      steps {
        sh "pwd"
        sh "ls -la"
        sh "git branch && git status"
      }
    }
    stage ('Git checkout main branch') {
      steps {
        checkout([$class: 'GitSCM',
                  userRemoteConfigs: [[url: "git@github.com:lykarik/test_only_repo.git",
                  credentialsId: 'jenkins-master-git-key']],
                  branches: [[name: "main"]],
                  extensions: [
                    [$class: 'CloneOption',
                      reference: "git@github.com:lykarik/test_only_repo.git",
                      shallow: true,
                      depth: "1"]
                  ]
        ])
      }
    }
  }
}
