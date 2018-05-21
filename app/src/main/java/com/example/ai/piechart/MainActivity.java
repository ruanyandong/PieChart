package com.example.ai.piechart;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ViewPager vpMain;

    private Button mLeftButton;
    private Button mRightButton;
    private TextView mTextView;


    /**
     * http://www.bejson.com/
     * json在线编辑
     */
    private String mJson = "[{\"date\":\"2016年5月\",\"obj\":[{\"title\":\"外卖\",\"value\":34},{\"title\":\"娱乐\",\"value\":21},{\"title\":\"其他\",\"value\":45}]}," +
            "{\"date\":\"2016年6月\",\"obj\":[{\"title\":\"外卖\",\"value\":42},{\"title\":\"娱乐\",\"value\":65},{\"title\":\"其他\",\"value\":12}]}," +
            "{\"date\":\"2016年7月\",\"obj\":[{\"title\":\"外卖\",\"value\":34},{\"title\":\"娱乐\",\"value\":123},{\"title\":\"其他\",\"value\":24}]}," +
            "{\"date\":\"2016年8月\",\"obj\":[{\"title\":\"外卖\",\"value\":145},{\"title\":\"娱乐\",\"value\":123},{\"title\":\"其他\",\"value\":124}]}]";

    private ArrayList<MonthBean> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWidget();

        initData();

        initView();

        initEvent();
    }

    private void initEvent() {
        vpMain.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                //int currentIndex=vpMain.getCurrentItem();
                mTextView.setText(mData.get(position).date);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentIndex = vpMain.getCurrentItem();
                if (currentIndex == 0) {
                    vpMain.setCurrentItem(mData.size() - 1);
                } else {
                    vpMain.setCurrentItem(currentIndex - 1, true);
                }
            }
        });

        mRightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentIndex = vpMain.getCurrentItem();
                if (currentIndex == mData.size() - 1) {
                    vpMain.setCurrentItem(0);
                } else {
                    vpMain.setCurrentItem(currentIndex + 1, true);
                }
            }
        });

    }

    //获取控件
    private void getWidget() {
        vpMain = findViewById(R.id.vp_main);
        mLeftButton = findViewById(R.id.left_button);
        mRightButton = findViewById(R.id.right_button);
        mTextView = findViewById(R.id.middle_textView);
    }

    private void initData() {
        Gson gson = new Gson();
        /**
         *
         使用protected 构造方法，是包保护措施，出了这个包，在别的包里面是不可以new这个对象的。 假设class A 的构造函数是protected的

         如果在别的包里还想用我这个类的功能，只能是用该类的子类了，创建一个子类B extends A，子类会继承方法。 这里可以使用匿名类。

         new A(){}.func1();

         但是在此处，调用的方法为public的，如果不是，就不可见了。

         但是为什么A的构造函数也是protected，但是可以new呢？

         这是因为在new子类对象的时候，在调用子类对象的构造方法时候会先调用父类的构造方法。

         子类的构造方法是在类体里面的。在子类的类体里面，对父类的protected变量和方法是可见的。

         */

        //创建匿名内部类相当于创建了这个类的一个子类
        mData = gson.fromJson(mJson, new TypeToken<ArrayList<MonthBean>>() {
        }.getType());
    }

    private void initView() {
        mTextView.setText(mData.get(0).date);
        vpMain.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                return PieFragment.newInstance(mData.get(position));
            }

            @Override
            public int getCount() {
                return mData.size();
            }
        });
    }

}
