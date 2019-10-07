/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duckonline;

import Util.QueuedCommand;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
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
        fov = 45f;
        translateX = 0.0f;
        translateY = 0.0f;
        translateZ = 2.0f;
    }
    
    public Matrix4f getWorldMVP(float x, float y, float z) {
        Vector3f camera =  new Vector3f(0, 2, -3.0f);
        Vector3f lookHere = new Vector3f(0, 0, 0);
        Matrix4f view = new Matrix4f().lookAt(
            camera,
            lookHere,
            // which axis is "up"
            new Vector3f(0.0f, 1.0f, 0.0f)
        );
        
        model = new Matrix4f().identity();
//        model.transform(new Vector4f(90f, 0, 90f, 0));
        model.translate(new Vector3f(x, 1 - y, z));
        
        proj = new Matrix4f().perspective(fov, width / height, 0.4f, 50.0f);
        Matrix4f mvp = proj.mul(view).mul(model);
        
        return mvp;
    }
    
    public Matrix4f getMVP(float rotation, float objectRot, float x, float y, float z) {
        Vector3f camera =  new Vector3f(0, 2, -5.0f);
        Vector3f lookHere = new Vector3f(0, 0, 0);
        Matrix4f view = new Matrix4f().lookAt(
            camera,
            lookHere,
            // which axis is "up"
            new Vector3f(0.0f, 1.0f, 0.0f)
        );
        
        model = new Matrix4f().identity();
        
        Quaternionf objQuat = new Quaternionf();
        model.translate(new Vector3f(x, y, z));
        objQuat.rotateY( - (float) Math.toRadians(objectRot));
        model.rotate(objQuat);
        
        Quaternionf quatH = new Quaternionf();
//        Quaternionf quatV = new Quaternionf();
        quatH.rotateY((float) Math.toRadians(rotation));
//        quatV.rotateX((float) Math.toRadians(45f));
        view.rotateAround(quatH, 0, 0, 0);
//        view.rotateAround(quatV, 0, 0, 0);
        
        
        proj = new Matrix4f().perspective(fov, width / height, 0.1f, 50.0f);
        Matrix4f mvp = proj.mul(view).mul(model);
        
//        mvp.rotateAround(quat, 0, 0, 0);
        return mvp;
    }

    public void strafeRight() {
        float tx = (float) (.1 * (float) Math.sin(Math.toRadians(yaw + 90)));
        translateX += tx;
        float tz = (float) (.1 * (float) Math.cos(Math.toRadians(yaw + 90)));
        translateZ -= tz;
        QueuedCommand.sendX(tx);
        QueuedCommand.sendZ(tz * -1);
    }
    
    public void strafeLeft() {
        float tx = (float) (.1 * (float) Math.sin(Math.toRadians(yaw - 90)));
        translateX += tx;
        float tz = (float) (.1 * (float) Math.cos(Math.toRadians(yaw - 90)));
        translateZ -= tz;
        QueuedCommand.sendX(tx);
        QueuedCommand.sendZ(tz * -1);
    }
    
    public void translateX(float x) {
        this.translateX += x;
    }
    
    public void forward() {
        float tx = (float) (.1 * (float) Math.sin(Math.toRadians(yaw)));
        translateX += tx;
        float tz = (float) (.1 * (float) Math.cos(Math.toRadians(yaw)));
        translateZ -= tz;
        QueuedCommand.sendX(tx);
        QueuedCommand.sendZ(tz * -1);
    }
    
    public void backward() {
        float tx = (float) (.1 * (float) Math.sin(Math.toRadians(yaw)));
        translateX -= tx;
        float tz = (float) (.1 * (float) Math.cos(Math.toRadians(yaw)));
        translateZ += tz;
        QueuedCommand.sendX(tx * -1);
        QueuedCommand.sendZ(tz);
    }
    
    public float getYaw() {
        return yaw;
    }
    
    public void setYaw(float yaw) {
        this.yaw = yaw;
    }
    
    public void up() {
        float ty = 0.1f;
        translateY += ty;
        QueuedCommand.sendY(ty);
    }
    
    public void down() {
        float ty = -0.1f;
        translateY += ty;
        QueuedCommand.sendY(ty);
    }
    
    public void zoomIn() {
        fov -= 0.1;
    }
    
    public void zoomOut() {
        fov += 0.1;
    }
    
    public void rotateHorizontal(float angle) {
        float newYaw = yaw + angle;
        if (newYaw < 0) {
            yaw = newYaw + 360;
        } else if (newYaw > 360) {
            yaw = newYaw - 360;
        } else if (newYaw == 360) {
            yaw = 0;
        } else {
            yaw += angle;
        }
        QueuedCommand.sendDir(yaw);
    }
    
    public String getLocation() {
        return translateX + ":" + translateY + ":" + translateZ;
    }
}
