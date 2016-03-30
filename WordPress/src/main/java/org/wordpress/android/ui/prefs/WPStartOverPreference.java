package org.wordpress.android.ui.prefs;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.wordpress.android.R;
import org.wordpress.android.WordPress;
import org.wordpress.android.models.Blog;
import org.wordpress.android.util.UrlUtils;

/**
 * Calypso-style Preference that has an icon and a widget in the correct place. If there is a button
 * with id R.id.button, an onPreferenceClick listener is added.
 */

public class WPStartOverPreference extends WPPreference {
    private String mButtonText;
    private int mButtonTextColor;
    private boolean mButtonTextAllCaps;

    public WPStartOverPreference(Context context, AttributeSet attrs) {
        super(context, attrs);

        mButtonTextColor = ContextCompat.getColor(context, R.color.black);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.WPStartOverPreference);

        for (int i = 0; i < array.getIndexCount(); ++i) {
            int index = array.getIndex(i);
            if (index == R.styleable.WPStartOverPreference_buttonText) {
                mButtonText = array.getString(index);
            } else if (index == R.styleable.WPStartOverPreference_buttonTextColor) {
                mButtonTextColor = array.getColor(index, ContextCompat.getColor(context, R.color.grey_dark));
            } else if (index == R.styleable.WPStartOverPreference_buttonTextAllCaps) {
                mButtonTextAllCaps = array.getBoolean(index, false);
            }
        }

        array.recycle();
    }

    @Override
    protected void onBindView(@NonNull View view) {
        super.onBindView(view);

        if (view.findViewById(R.id.button) != null) {
            final WPStartOverPreference wpStartOverPreference = this;

            Button button = (Button) view.findViewById(R.id.button);
            button.setText(mButtonText);
            button.setTextColor(mButtonTextColor);
            button.setAllCaps(mButtonTextAllCaps);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getOnPreferenceClickListener().onPreferenceClick(wpStartOverPreference);
                }
            });
        }

        if (view.findViewById(R.id.domain) != null) {
            TextView textView = (TextView) view.findViewById(R.id.domain);
            Blog blog = WordPress.getCurrentBlog();
            textView.setText(UrlUtils.getHost(blog.getHomeURL()));
        }
    }
}
