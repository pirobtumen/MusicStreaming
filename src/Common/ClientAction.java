package Common;

/**
 *
 * - Alberto Sola
 */

public class ClientAction {
    /*
        Store the client action and data in a request.
     */

    // ------------------------------------------------------------------------

    public enum ActionType {NONE, GET_PLAYLIST, GET_SONG};
    private static final int ACTION_CODE_NONE = 0;
    private static final int ACTION_CODE_GET_PLAYLIST = 1;
    private static final int ACTION_CODE_GET_SONG = 2;

    private ActionType action = ActionType.NONE;
    private String data = null;

    // ------------------------------------------------------------------------

    private void parse(String input_action){
        /*
         Convert a string into an action.
         */
        String [] split = input_action.split(",");
        int code = Integer.parseInt(split[0]);

        if(split.length == 2)
            data = split[1];

        switch (code){
            case ACTION_CODE_GET_PLAYLIST:
                action = ActionType.GET_PLAYLIST;
                break;
            case ACTION_CODE_GET_SONG:
                action = ActionType.GET_SONG;
                break;
            default:
                action = ActionType.NONE;
                break;
        }

    }

    // ------------------------------------------------------------------------

    public ClientAction(){}

    // ------------------------------------------------------------------------

    public ClientAction(String action){
        setAction(action);
    }

    // ------------------------------------------------------------------------

    public ActionType getAction(){
        return action;
    }

    // ------------------------------------------------------------------------

    public void setAction(ActionType newAction){
        action = newAction;
    }

    // ------------------------------------------------------------------------

    public void setAction(String newAction){
        parse(newAction);
    }

    // ------------------------------------------------------------------------

    public String getCode(){
        int code;

        switch (action){
            case GET_PLAYLIST:
                code = ACTION_CODE_GET_PLAYLIST;
                break;
            case GET_SONG:
                code = ACTION_CODE_GET_SONG;
                break;
            default:
                code = ACTION_CODE_NONE;
                break;
        }

        return Integer.toString(code);
    }

    // ------------------------------------------------------------------------

    public String getData() {
        return data;
    }

    // ------------------------------------------------------------------------

    public void setData(String data){
        this.data = data;
    }

    // ------------------------------------------------------------------------

    public String getAll(){
        /*
            Return the Action and the Data in a String.

            Code,Data
         */
        String actionData = getCode();

        if(data != null)
            actionData += "," + data;

        return actionData;
    }

    // ------------------------------------------------------------------------

    @Override
    public String toString(){
        /*
            Convert an action in a string.
         */
        String action_name;

        switch (action){
            case GET_PLAYLIST:
                action_name = "ACTION.GET_PLAYLIST";
                break;
            case GET_SONG:
                action_name = "ACTION.GET_SONG";
                break;
            default:
                action_name = "ACTION.NONE";
                break;
        }

        return action_name;
    }

}
