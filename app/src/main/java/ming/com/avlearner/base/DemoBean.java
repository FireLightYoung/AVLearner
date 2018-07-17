package ming.com.avlearner.base;

/**
 * Created by ming on 17/9/15.
 */

public class DemoBean {

    String demoName;
    String description;
    Class mActivityClass;


    public DemoBean(String demoName, String description, Class activityClass) {
        this.demoName = demoName;
        this.description = description;
        mActivityClass = activityClass;
    }

    public String getDemoName() {
        return demoName;
    }

    public void setDemoName(String demoName) {
        this.demoName = demoName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Class getActivityClass() {
        return mActivityClass;
    }

    public void setActivityClass(Class activityClass) {
        mActivityClass = activityClass;
    }
}
