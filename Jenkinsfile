pipeline {
  agent {label 'master'}

  stages {
    stage ('Git checkout main branch') {
      steps {
        sh "pwd"
        sh "ls -la"
        sh "git status"
      }
    }
  }
}
