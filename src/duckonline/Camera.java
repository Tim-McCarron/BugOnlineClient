/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duckonline;

import Util.QueuedCommand;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector3f;


/**
 *
 * @author abe
 */
public class Camera {
    
    private int width, height;
    Matrix4f model;
    Matrix4f proj;
    float translateX;
    float translateY;
    float translateZ;
    float fov;
    float strafe = 0;
    // spin
    float yaw = 0;
    // up down
    float pitch = 0;
    
    public Camera(int width, int height) {
        this.width = width;
        this.height = height;
        fov = 45.0f;
        translateX = 0.0f;
        translateY = 0.0f;
        translateZ = 2.0f;
    }
    
    public Matrix4f getMVP() {
               
        Vector3f camera =  new Vector3f(translateX, translateY, translateZ);
        Vector3f lookHere = new Vector3f(translateX, translateY, translateZ - 2.0f);
        Matrix4f view = new Matrix4f().lookAt(
            camera,
            lookHere,
            // which axis is "up"
            new Vector3f(0.0f, 1.0f, 0.0f)
        );
        
        Quaternionf quat = new Quaternionf();
        quat.rotateY((float) Math.toRadians(yaw));
        view.rotateAround(quat, translateX, translateY, translateZ - 2);

        model = new Matrix4f().identity();
        proj = new Matrix4f().perspective(fov, width / height, 0.1f, 10.0f);
        Matrix4f mvp = proj.mul(view).mul(model);
        
        return mvp;
    }
    
    public void strafeRight() {
        float tx = (float) (.1 * (float) Math.sin(Math.toRadians(yaw + 90)));
        translateX += tx;
        float tz = (float) (.1 * (float) Math.cos(Math.toRadians(yaw + 90)));
        translateZ -= tz;
        QueuedCommand.sendUp(1);
    }
    
    public void strafeLeft() {
        translateX += .1 * (float) Math.sin(Math.toRadians(yaw - 90));
        translateZ -= .1 *
                (float) Math.cos(Math.toRadians(yaw - 90));
    }
    
    public void translateX(float x) {
        this.translateX += x;
    }
    
    public void forward() {
        translateX += .1 * (float) Math.sin(Math.toRadians(yaw));
        translateZ -= .1 * (float) Math.cos(Math.toRadians(yaw));
    }
    
    public void backward() {
        translateX -= .1 * (float) Math.sin(Math.toRadians(yaw));
        translateZ += .1 * (float) Math.cos(Math.toRadians(yaw));
    }
    
    public void up() {
        translateY += .1;
    }
    
    public void down() {
        translateY -= .1;
    }
    
    public void zoomIn() {
        fov -= 0.1;
        System.out.println(fov);
    }
    
    public void zoomOut() {
        fov += 0.1;
        System.out.println(fov);
    }
    
    public void rotateHorizontal(double angle) {
        yaw += angle;
    }
    
    public String getLocation() {
        return translateX + ":" + translateY + ":" + translateZ;
    }
}
