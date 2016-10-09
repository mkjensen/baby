/*
 * Copyright 2016 Martin Kamp Jensen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.mkjensen.baby;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener {

  private DrawerLayout drawer;

  private ActionBarDrawerToggle drawerToggle;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    initDrawer();
    initNavigation(savedInstanceState);
    initFloatingActionButton();
  }

  private void initDrawer() {
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    drawer = (DrawerLayout) findViewById(R.id.drawer);
    drawerToggle = new ActionBarDrawerToggle(
        this,
        drawer,
        toolbar,
        R.string.navigation_open,
        R.string.navigation_close);
    drawer.addDrawerListener(drawerToggle);
  }

  private void initNavigation(@Nullable Bundle savedInstanceState) {
    NavigationView navigation = (NavigationView) findViewById(R.id.navigation);
    if (savedInstanceState == null) {
      MenuItem item = navigation.getMenu().findItem(R.id.navigation_overview);
      item.setChecked(true);
      onNavigationItemSelected(item);
    }
    navigation.setNavigationItemSelectedListener(this);
  }

  private void initFloatingActionButton() {
    FloatingActionButton addButton = (FloatingActionButton) findViewById(R.id.action_add_measurements);
    addButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Snackbar.make(view, getString(R.string.action_add_measurements), Snackbar.LENGTH_LONG).show();
      }
    });
  }

  @Override
  protected void onPostCreate(@Nullable Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    drawerToggle.syncState();
  }

  @Override
  public void onBackPressed() {
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    drawerToggle.onConfigurationChanged(newConfig);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    return drawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
  }

  @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    activateDummyFragment(item.getTitle().toString());
    drawer.closeDrawer(GravityCompat.START);
    return true;
  }

  private void activateDummyFragment(String title) {
    Bundle args = new Bundle();
    args.putString(DummyFragment.TITLE, title);
    Fragment fragment = new DummyFragment();
    fragment.setArguments(args);
    FragmentManager fragmentManager = getSupportFragmentManager();
    fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
  }

  public static final class DummyFragment extends Fragment {

    private static final String TITLE = "title";

    public DummyFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
      String title = getArguments().getString(TITLE);
      FragmentActivity activity = getActivity();
      activity.setTitle(title);
      TextView titleText = new TextView(activity);
      titleText.setText(title);
      return titleText;
    }
  }
}
