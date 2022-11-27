package com.alex.greenteacomposeexample


import ca.gaket.tea.GreenTeaViewModel

class ViewModelGreenTea constructor(
    dependencies: GreenTeaFeature.Dependencies
) : GreenTeaViewModel<GreenTeaFeature.State,GreenTeaFeature.Message,GreenTeaFeature.Dependencies>(
init = GreenTeaFeature.Logic.initialUpdate,
update = GreenTeaFeature.Logic::update,
dependencies = dependencies
)