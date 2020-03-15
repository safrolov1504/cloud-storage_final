package Communication;

import Controllers.Controller;
import WorkingWithMessage.GetMessage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MyClientServer {
    //for communication with server
    private static final String HOST_ADDRESS_PROP = "server.address";
    private static final String HOST_PORT_PROP = "server.port";
    private String hostAddress;
    private int hostPort;

    private GetMessage getMessage;

    private Controller controller;
    private Network network;

    public MyClientServer(Controller controller) {
        this.controller = controller;
        //есть какая то камуникация с контроллером
        initialise();
    }

    private void initialise() {
        readProperties();
        startConnectionToServer();
    }

    //read properties for connection
    private void readProperties() {
        Properties serverProperty = new Properties();
        try(InputStream inputStream = getClass().getResourceAsStream("/application.properties")){
            serverProperty.load(inputStream);
            hostAddress = serverProperty.getProperty(HOST_ADDRESS_PROP);
            hostPort = Integer.parseInt(serverProperty.getProperty(HOST_PORT_PROP));
        } catch (IOException e) {
            new RuntimeException("Failed to read application.properties file", e);
        }
    }

    private void startConnectionToServer() {
        this.network = new Network(hostAddress, hostPort, this);
        getMessage = new GetMessage(network,controller);
    }

    public void sendMessage(byte[] outByte) {
        network.sendMessage(outByte);
    }

    public void processRetrievedMessage(byte[] innnerBute) {
        getMessage.workingWithInnerMessage(innnerBute);
    }
}
