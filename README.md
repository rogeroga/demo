# Project
Interview coding test for Rogelio Robles


## Execution
To execute from the command line use maven and do:

    mvn -Dspring.profiles.active=dev spring-boot:run

You can select the deployment profile by changing to the value to one of the following values:

    dev, stage or prod.


## Configuration
To change the configuration you can edit the .properties files located in the directory:

    src/main/resources
    
These are the defined configuration values:

    ## Queue max limit of parallel executions
    #
    queue.limit=5
    
    ## Optimization Solver timeout in secs.
    #
    consumer.timeout=3


The queue.limit is the max limit of number of parallel executions allowed according ot the license agreement.

The consumer.timeout is the max time in seconds allowed for a single call to the Optimization Solver

  

