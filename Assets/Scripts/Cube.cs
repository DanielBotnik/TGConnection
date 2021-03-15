using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class Cube : MonoBehaviour
{
    private const float speed = 30f;
    private ThinkGear thinkgear;
    private bool changeColor = false;
    private float lastClick;

    public void Start()
    {
        thinkgear = GameObject.Find("ThinkGear").GetComponent<ThinkGear>();
        thinkgear.UpdateMeditationEvent += CheckMeditation;
        thinkgear.UpdateConnectedStateEvent += OnThinkGearConnected;
        thinkgear.UpdateDisconnectedStateEvent += OnThinkGearDisconnect;
        Random.InitState(System.DateTime.Now.Millisecond);
    }
    void Update()
    {
        transform.Rotate(speed * Time.deltaTime, 2 * speed * Time.deltaTime, -speed * Time.deltaTime);
        if (changeColor && (Time.time - lastClick) < 2)
            ChangeColor();
    }

    private void CheckMeditation(int value)
    {
        lastClick = Time.time;
        Debug.Log("Meditation: " + value);
        if(value >= 50)
            changeColor = true;
        else
            changeColor = false;
    }

    private void OnMouseDown()
    {
        thinkgear.StartMonitoring();
    }

    private void OnThinkGearConnected()
    {
        thinkgear.StartMonitoring();
        Text text = GameObject.Find("ConnectText").GetComponent<Text>();
        text.text = "Connected";
        text.color = Color.green;
    }

    private void OnThinkGearDisconnect()
    {
        Text text = GameObject.Find("ConnectText").GetComponent<Text>();
        text.text = "Not Connected";
        text.color = Color.red;
    }

    private void ChangeColor()
    {
        Renderer renderer = this.GetComponent<Renderer>();
        Material mat = renderer.material;
        Color color = mat.color;
        mat.color = new Color((color.r + Random.Range(-2f,2f) * Time.deltaTime) % 256, (color.g + Random.Range(-2f, 2f) * Time.deltaTime) % 256, (color.b + Random.Range(-2f, 2f) * Time.deltaTime) % 256, (color.a + Random.Range(-0.001f,0.001f) * Time.deltaTime) % 1);
    }

}
