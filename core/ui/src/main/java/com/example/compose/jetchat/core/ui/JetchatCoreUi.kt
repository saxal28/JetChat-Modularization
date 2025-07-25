/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.compose.jetchat.core.ui

// Re-export theme system components
import com.example.compose.jetchat.core.ui.theme.JetchatTheme
import com.example.compose.jetchat.core.ui.theme.JetchatDarkColorScheme
import com.example.compose.jetchat.core.ui.theme.JetchatLightColorScheme
import com.example.compose.jetchat.core.ui.theme.JetchatTypography

// Re-export colors
import com.example.compose.jetchat.core.ui.theme.Blue10
import com.example.compose.jetchat.core.ui.theme.Blue20
import com.example.compose.jetchat.core.ui.theme.Blue30
import com.example.compose.jetchat.core.ui.theme.Blue40
import com.example.compose.jetchat.core.ui.theme.Blue80
import com.example.compose.jetchat.core.ui.theme.Blue90

// Re-export components
import com.example.compose.jetchat.core.ui.components.JetchatIcon
import com.example.compose.jetchat.core.ui.modifiers.baselineHeight
import com.example.compose.jetchat.core.ui.modifiers.BaselineHeightModifier

// This file serves as the public API for the core:ui module
// Import from this file to access all core UI components and theming