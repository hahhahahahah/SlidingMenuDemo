package com.org.zl.slidingmenudemo;

import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.org.zl.slidingmenudemo.utils.ScreenUtils;

public class MainActivity extends AppCompatActivity {
    private SlidingMenu menu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadMenu();
    }
    //这里已经定型，不要改动
    private void loadMenu() {
        //获取屏幕的宽度
        int with = ScreenUtils.getScreenWidth(this);
        //初始化侧边栏
        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        // 设置触摸屏幕的模式
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        //设置SlidingMenu菜单的宽度
        menu.setBehindWidth(with / 2);
        //        menu.setShadowDrawable(R.drawable.shadow);//设置阴影图片
        //        menu.setShadowWidthRes(R.dimen.shadow_width);//设置阴影图片的宽度
        menu.setBackgroundColor(Color.BLACK);
        // 设置渐入渐出效果的值
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(MainActivity.this, SlidingMenu.SLIDING_CONTENT);
        //为侧滑菜单设置布局
        menu.setMenu(R.layout.slidingmenuleft);

        //设置菜单UI的移动效果(这里是hi改变的地方)
        menu.setBehindCanvasTransformer(new SlidingMenu.CanvasTransformer() {
            @Override
            public void transformCanvas(Canvas canvas, float percentOpen) {
                float scale = 0.75f + 0.25f * percentOpen;
                canvas.scale(scale, scale, -canvas.getWidth() / 2, canvas.getHeight() / 2);
            }
        });

        //设置activity层内容UI移动效果
        //此方法为slidingmenu中自己增加的方法
        menu.setAboveCanvasTransformer(new SlidingMenu.CanvasTransformer() {
            @Override
            public void transformCanvas(Canvas canvas, float percentOpen) {
                float scale = 1f - 0.25f * percentOpen;
                canvas.scale(scale, scale, 0, canvas.getHeight() / 2);
            }
        });
    }

    @Override
    protected void onDestroy() {
        if(menu.isSlidingEnabled()){
            menu.showContent();
        }
        super.onDestroy();
    }
}
