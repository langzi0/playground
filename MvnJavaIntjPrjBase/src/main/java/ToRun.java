/**
 * PACKAGE_NAME
 *
 * @author xbian
 * @since
 */
public class ToRun{
    public Runnable runnable;
    // configure it as false to not running it.
    public boolean willRun;
    public ToRun(Runnable runnable, boolean willRun){
        this.runnable = runnable;
        this.willRun = willRun;
    }
}