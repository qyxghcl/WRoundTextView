# WRoundTextView

Step 1. Add the JitPack repository to your build file

```
//Add it in your root build.gradle at the end of repositories:
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

Step 2. Add the dependency

```
dependencies {
	implementation 'com.github.qyxghcl:WRoundTextView:v1.0.3'
}
```

**可以指定圆角,背景色,stroke,渐变色的 TextView**

属性:

| 属性              | 作用             |
| ----------------- | ---------------- |
| bgColorNormal     | 常规背景颜色     |
| bgColorSelect     | 选中背景颜色     |
| halfRadius        | 一半的圆角       |
| radius            | 圆角角度         |
| leftTopRadius     | 左上圆角         |
| leftBottomRadius  | 左下圆角         |
| rightTopRadius    | 右上圆角         |
| rightBottomRadius | 右下圆角         |
| strokeWidth       | 轮廓线宽         |
| strokeColorNormal | 轮廓线常规颜色   |
| strokeColorSelect | 轮廓线选中颜色   |
| startColor        | 渐变色开始颜色   |
| endColor          | 渐变色结束颜色   |
| angle             | 0-315 逆时针旋转 |

**API**

```
/**
 * 设置选中状态
 *
 * @param select true:选中 false:未选中
 */
public void setRoundTextViewSelect(boolean select) {

}
```

## 注意:

```
//优先级:
如果设置其中一项,则之后项无效
halfRadius > radius > leftTopRadius

//startColor与endColor
全部设置,渐变色才会生效,默认angle为0,每次增加45,逆时针旋转

//(bgColorNormal/bgColorSelect)与(startColor/endColor)不能同时生效
//优先级:
bgColorNormal/bgColorSelect设置任意一项则startColor/endColor属性失效

//strokeWidth
无法设置小数,setStroke()并不支持float的设置,会被强转掉
```

## 使用:

其余属性与 textView 完全相同
