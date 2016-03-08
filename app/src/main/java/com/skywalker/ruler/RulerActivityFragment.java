package com.skywalker.ruler;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.skywalker.ruler.view.HandleView;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import javax.inject.Inject;

public class RulerActivityFragment extends Fragment {

    @Inject Bus bus;
    @Bind(R.id.handle_view_1) HandleView handleView1;
    @Bind(R.id.handle_view_2) HandleView handleView2;
    @Bind(R.id.ruler_text) TextView rulerText;

    private float pixelsPerOneSixteenInch;

    public RulerActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((AppComponentProvider) getActivity()).getComponent().inject(this);

        bus.register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_ruler, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    private void initViews() {
        handleView1.setMessageBus(bus);
        handleView1.setSecondHandleView(handleView2);
        handleView2.setMessageBus(bus);
        handleView2.setSecondHandleView(handleView1);
        handleView2.setY(200.0f);

        pixelsPerOneSixteenInch = getResources().getDisplayMetrics().ydpi / 16.0f;
    }

    @Subscribe
    public void showValue(HandleMoveEvent event) {
        int number = (int)(event.number / pixelsPerOneSixteenInch);
        int intNumber = number / 16;
        int fractionNumber = number % 16;

        String text;
        if (intNumber == 0) {
            text = getString(R.string.inches_only_fraction, fractionNumber);
        } else {
            text = getResources().getQuantityString(R.plurals.inches, intNumber, intNumber);

            if (fractionNumber > 0) {
                text += getString(R.string.inches_int_and_fraction, fractionNumber);
            }
        }

        rulerText.setText(text);
    }

    @OnClick(R.id.parent_panel)
    protected void onClickParentPanel() {
        if (handleView1.getVisibility() == View.GONE) {
            handleView1.setVisibility(View.VISIBLE);
            handleView2.setVisibility(View.VISIBLE);
        }
    }
}
