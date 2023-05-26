def call () {
  sh "echo IM FROM SHARED LIB"
}

def ansible_fake (ansible_limit, inventory) {
  sh """
    echo "Start playbook -l ${ansible_limit} -i ${inventory}"
  """
}
