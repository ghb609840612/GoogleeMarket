package com.snow.night.googleemarket.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Administrator on 2016/4/16.
 */
public class SubjectImagview extends ImageView{

    private int heigtselfmeasureSpec;

    public SubjectImagview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 用父类的测量规则
     * 由于子控件本身的宽度为match-parent 所已子控件的宽度是确定的 为父控件的宽度
     * 而高度为wrap-content 不确定的 所以测量之后决定  测量之后用原来的高度 而宽度用
     * 父控件的宽度  导致宽高比发生变化 导致图片变形
     * widthMeasureSpec中由两部分值组成 一部分为测量模式 占前两位，一部分为实际值
     * 测量模式有三种 分别为 at-most （wrap-content） exactly（确定的值或者 match-parent 因为父控件的
     * 宽高是确定的） unspecific （不确定的）
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        Drawable drawable = getDrawable();  //获取src指定的图片
        if(drawable!= null)
        {
            int picHeight = drawable.getMinimumHeight();  //获取图片的真实高度
            int picWidth = drawable.getMinimumWidth();  //获取图片的真实高度
            //计算图片的宽高比
            float scaleHbyw = (float)picHeight/picWidth;
            //获取系统测量的宽度
            int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
            //根据宽高比 及 测量出的宽度 得到高度
            int scaleHeight = (int) (measureWidth*scaleHbyw);
            //将计算好的高度 制作成一个测量规则 交给ondraw
            heigtselfmeasureSpec = MeasureSpec.makeMeasureSpec(scaleHeight, MeasureSpec.EXACTLY);

        }

        super.onMeasure(widthMeasureSpec, heigtselfmeasureSpec);
    }
    /**
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
