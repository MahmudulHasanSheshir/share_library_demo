
def call(Map params = [:]) {
    def credentialsId = params.credentialsId ?: error("deployK8sResources: 'credentialsId' is required")
    def kubeConfigs = params.kubeConfigs ?: error("deployK8sResources: 'kubeConfigs' list is required")
    def kubectlPath = params.get('kubectlPath', 'kubectl')
    def clusterConfig = params.get('clusterConfig', [:])

    withKubeCredentials(kubectlCredentials: [[
        caCertificate: clusterConfig.caCertificate ?: '',
        clusterName: clusterConfig.clusterName ?: '',
        contextName: clusterConfig.contextName ?: '',
        credentialsId: credentialsId,
        namespace: clusterConfig.namespace ?: '',
        serverUrl: clusterConfig.serverUrl ?: ''
    ]]) {
        kubeConfigs.each { configFile ->
            sh "${kubectlPath} apply -f ${configFile}"
        }
    }
}
