def call(Map params = [:]) {
    def credentialsId = params.credentialsId ?: error("deployK8s: 'credentialsId' is required")
    def kubeConfigs = params.kubeConfigs ?: error("deployK8s: 'kubeConfigs' is required and should be a list of files")
    def kubectlPath = params.get('kubectlPath', 'kubectl')
    def clusterConfig = params.get('clusterConfig', [:])
    def namespace = clusterConfig.namespace ?: 'default'

    withKubeCredentials(kubectlCredentials: [[
        caCertificate: clusterConfig.caCertificate ?: '',
        clusterName: clusterConfig.clusterName ?: '',
        contextName: clusterConfig.contextName ?: '',
        credentialsId: credentialsId,
        namespace: namespace,
        serverUrl: clusterConfig.serverUrl ?: ''
    ]]) {
        kubeConfigs.each { configFile ->
            sh "${kubectlPath} apply -f ${configFile}"
        }
        
        // Optional: Get services in the specified namespace
        if (params.get('listServices', false)) {
            sh "${kubectlPath} get services -n ${namespace}"
        }
    }
}
