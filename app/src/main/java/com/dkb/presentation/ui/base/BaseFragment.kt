package com.dkb.presentation.ui.base

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

abstract class BaseFragment: Fragment() {
    protected val navController by lazy { findNavController() }
}