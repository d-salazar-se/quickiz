import React, { Component } from 'react';
import Grid from '@material-ui/core/Grid';

class HomeComponent extends Component {
  render() {
    return (
      <Grid
        container
        direction="row"
        justify="center"
        alignItems="center"
        spacing={16}
      >
        <img alt="Quickiz" src={'/images/quickiz.png'} width="400" />
      </Grid>
    );
  }
}

export default HomeComponent;
