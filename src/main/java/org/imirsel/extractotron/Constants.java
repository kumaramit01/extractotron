package org.imirsel.extractotron;


/**
 * Constant values used throughout the application.
 *
 */
public final class Constants {

    private Constants() {
        // hide me
    }
    //~ Static fields/initializers =============================================

    /**
     * The name of the ResourceBundle used in this application
     */
    public static final String BUNDLE_KEY = "ApplicationResources";

    /**
     * File separator from System properties
     */
    public static final String FILE_SEP = System.getProperty("file.separator");

    /**
     * User home from System properties
     */
    public static final String USER_HOME = System.getProperty("user.home") + FILE_SEP;

    /**
     * The name of the configuration hashmap stored in application scope.
     */
    public static final String CONFIG = "appConfig";

    /**
     * Session scope attribute that holds the locale set by the user. By setting this key
     * to the same one that Struts uses, we get synchronization in Struts w/o having
     * to do extra work or have two session-level variables.
     */
    public static final String PREFERRED_LOCALE_KEY = "org.apache.struts2.action.LOCALE";

    /**
     * The request scope attribute under which an editable user form is stored
     */
    public static final String USER_KEY = "userForm";

    /**
     * The request scope attribute that holds the user list
     */
    public static final String USER_LIST = "userList";
    
    /**
     * The requesr scope attribute that holds the collection list
     */
    public static final String COLLECTION_LIST = "collectionList";
    
    public static final String COLLECTION = "collection";
    
    public static final String PROJECT_LIST = "projectList";
    
    public static final String PROJECT = "project";
    
	public static final String EC_LIST = "executorList";
	
	public static final String EC = "executor";
	
	public static final String MAP = "map";



    /**
     * The request scope attribute for indicating a newly-registered user
     */
    public static final String REGISTERED = "registered";

    /**
     * The name of the Administrator role, as specified in web.xml
     */
    public static final String ADMIN_ROLE = "ROLE_ADMIN";

    /**
     * The name of the User role, as specified in web.xml
     */
    public static final String USER_ROLE = "ROLE_USER";

    /**
     * The name of the user's role list, a request-scoped attribute
     * when adding/editing a user.
     */
    public static final String USER_ROLES = "userRoles";

    /**
     * The name of the available roles list, a request-scoped attribute
     * when adding/editing a user.
     */
    public static final String AVAILABLE_ROLES = "availableRoles";

    /**
     * The name of the CSS Theme setting.
     */
    public static final String CSS_THEME = "csstheme";

	public static final String EXTRACTOR_LIST = "extractorList";

	public static final String EXTRACTOR = "extractor";
	
	public static final String RUNNING ="RUNNING";
	public static final String ABORT_REQUESTED = "ABORT_REQUESTED";
	public static final String CREATED = "CREATED";
	public static final String ABORTED = "ABORTED";
	public static final String FINISHED = "FINISHED";
	
	
	

//	String[] phases = new String[]{"CREATED","RUNNING","ABORTED","ABORT_REQUESTED","FINISHED"};
	

	
}
