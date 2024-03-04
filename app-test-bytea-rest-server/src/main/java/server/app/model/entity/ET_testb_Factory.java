package server.app.model.entity;

public class ET_testb_Factory {

    //	***************************************************************************
    //	** Attributes
    //	***************************************************************************
	
    /** Singleton instance. */
    static private ET_testb_Factory instance;
    

    // Singleton
    static {
            synchronized (ET_testb_Factory.class) {
                    if (instance == null) {
                    	instance = new ET_testb_Factory();
                    	entityInstance = new ET_testb_EntityService();
                    }
            }
    }

    static private ET_testb_EntityService entityInstance;

    //	***************************************************************************
    //	** Singleton-Access
    //	***************************************************************************

    /**
     * Instance Getter
     * 
     * @return
     */
    static public ET_testb_EntityService getService() {
        return entityInstance;
    }


 
}

